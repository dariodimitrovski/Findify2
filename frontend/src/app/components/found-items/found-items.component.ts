import { Component, OnInit } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { PostDetailsModalComponent } from '../post-details-modal/post-details-modal.component';
import { PostService } from '../../services/post.service';
import { Post } from '../../models/Post';
import { RouterLink } from '@angular/router';
import { FilterSectionComponent } from "../filter-section/filter-section.component";
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CategoryService } from '../../services/category.service';
import { FilterService } from '../../services/filter.service';
import { MunicipalityService } from '../../services/municipality.service';
import { Category } from '../../models/Category';
import { Municipality } from '../../models/Municipality';
import { NgFor, NgIf } from '@angular/common';
import { DomSanitizer } from '@angular/platform-browser';
import { Subject, debounceTime, distinctUntilChanged } from 'rxjs';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-found-items',
  standalone: true,
  templateUrl: './found-items.component.html',
  styleUrl: './found-items.component.scss',
  imports: [NavBarComponent, PostDetailsModalComponent, RouterLink, FilterSectionComponent, NgFor, ReactiveFormsModule, NgIf, MatPaginatorModule]
})
export class FoundItemsComponent {
  constructor(
    private postService: PostService,
    private municipalityService: MunicipalityService,
    private categoryService: CategoryService,
    private formBuilder: FormBuilder,
    private filterService: FilterService,
    private sanitizer: DomSanitizer
  ) { }
  posts: Post[] = []
  filtered: Post[] = [];

  form!: FormGroup;
  filter = false;
  sortOpened: boolean = false;
  sortingMethod: string = 'Најстари прво';

  query$: Subject<string> = new Subject()
  q: string = ''
  totalItems = this.getItemSize; 
  pageSize = 10;
  currentPage = 0;

  pageChanged(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.getFoundItems(this.currentPage, this.pageSize);
  }

  ngOnInit(): void {
    this.getMunicipalities();
    this.getCategories();

    this.form = this.formBuilder.group({
      title: '',
      category: '',
      municipality: '',
      order: ''
    });

    this.query$
      .pipe(
        debounceTime(800),
        distinctUntilChanged(),
      )
      .subscribe(it => {
        this.q = it;
        this.onSubmitFilter()
      })
    this.getItemSize();

    this.getFoundItems(this.currentPage, this.pageSize);
  }

  toggleSort() {
    this.sortOpened = !this.sortOpened;
  }

  search(query: string) {
    this.query$.next(query)
  }

  setCategory(category: string = '') {
    this.form.get('category')!!.setValue(category);
    this.onSubmitFilter();
  }

  setSortingMethod(method: string) {
    this.form.get('order')!!.setValue(method);
    this.sortingMethod = method;
    this.onSubmitFilter();
  }

  onSubmitFilter(): void {
    this.filter = true;
    if (this.form.invalid) {
      console.log("Form is invalid");
      return;
    }

    const formData = new FormData();
    formData.append("title", this.q);
    formData.append("category", this.form.get('category')?.value || '');
    formData.append("municipality", this.form.get('municipality')?.value || '');
    formData.append("state", "ACTIVE_FOUND")
    formData.append("order", this.form.get('order')?.value || 'Најнови прво');


    this.filterService.filterPosts(formData).subscribe({
      next: (data) => {
        this.filtered = data;
        data.forEach((element) => {
          this.postService.getPostImage(element.id).subscribe((ImageData) => {
            const imageUrl = URL.createObjectURL(new Blob([ImageData]));
            element.image = this.sanitizer.bypassSecurityTrustUrl(imageUrl);
          });
        });
      },
      error: (error) => {
        console.error('Error fetching filtered items', error);
      }
    });
  }

  municipalities: Municipality[] = []

  getMunicipalities() {
    this.municipalityService.getMunicipalities().subscribe((it) => {
      this.municipalities = it;
    });
  }

  categories: Category[] = []

  getCategories() {
    this.categoryService.getCategories().subscribe((it) => {
      this.categories = it;
    })
  }
  getFoundItems(page: number, size: number): void {
    this.postService.getFoundItems(page, size).subscribe({
      next: (data) => {
        this.posts = data;
        data.forEach((element) => {
          this.postService.getPostImage(element.id).subscribe((imageData) => {
            const imageUrl = URL.createObjectURL(new Blob([imageData]));
            element.image = this.sanitizer.bypassSecurityTrustUrl(imageUrl);
          });
        });
      },
      error: (error) => {
        console.error('Error fetching lost items:', error);
      }
    });
  }

  getItemSize() {
    this.postService.getFoundItemsSize().subscribe({
      next: (size) => {
        this.totalItems = size;
        console.log(size)
      },
      error: (error) => {
        console.error('Error fetching lost items size:', error);
      }
    }
    );
  }

  reloadPage() {
    window.location.reload();
  }
}

