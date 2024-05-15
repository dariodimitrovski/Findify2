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

@Component({
    selector: 'app-found-items',
    standalone: true,
    templateUrl: './found-items.component.html',
    styleUrl: './found-items.component.scss',
    imports: [NavBarComponent, PostDetailsModalComponent, RouterLink, FilterSectionComponent, NgFor, ReactiveFormsModule, NgIf]
})
export class FoundItemsComponent{
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

  query$: Subject<string> = new Subject()
  q: string = ''

  ngOnInit(): void {
    this.getMunicipalities();
    this.getCategories();

    this.form = this.formBuilder.group({
      title: '',
      category: '',
      municipality: ''
    });

    this.query$
      .pipe(
        debounceTime(400),
        distinctUntilChanged(),
      )
      .subscribe(it => {
        this.q = it;
        this.onSubmitFilter()
      })

    this.getFoundItems();
  }
  search(query: string) {
    this.query$.next(query)
  }

  onSubmitFilter(): void {
    this.filter = true;
    if (this.form.invalid) {
      console.log("Form is invalid");
      return;
    }
  
    const formData = new FormData();
    // formData.append("title", this.form.get('title')?.value || '');
    formData.append("title", this.q);
    formData.append("category", this.form.get('category')?.value || '');
    formData.append("municipality", this.form.get('municipality')?.value || '');
    formData.append("state", "ACTIVE_FOUND")

  
    this.filterService.filterPosts(formData).subscribe({
      next: (data) => {
        this.filtered = data;
        data.forEach((element) => {
          this.postService.getPostImage(element.id).subscribe((ImageData)=> {
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
  getFoundItems(): void {
    this.postService.getFoundItems().subscribe({
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

  reloadPage() {
    window.location.reload();
  }
  }

