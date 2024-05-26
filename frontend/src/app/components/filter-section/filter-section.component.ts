import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HomeComponent } from '../home/home.component';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoryService } from '../../services/category.service';
import { MunicipalityService } from '../../services/municipality.service';
import { PostService } from '../../services/post.service';
import { Category } from '../../models/Category';
import { Municipality } from '../../models/Municipality';
import { FilterService } from '../../services/filter.service';
import { Post } from '../../models/Post';

@Component({
  selector: 'app-filter-section',
  standalone: true,
  imports: [RouterLink, HomeComponent,  ReactiveFormsModule],
  templateUrl: './filter-section.component.html',
  styleUrl: './filter-section.component.scss'
})
export class FilterSectionComponent{

  errorMessage: string = ''
  form!: FormGroup
  posts: Post[] = []
 

  constructor(private municipalityService: MunicipalityService,
    private formBuilder: FormBuilder,
    private service: FilterService,
    private router: Router,
    private categoryService: CategoryService,
    private route: ActivatedRoute) { }

  get f(): { [key: string]: AbstractControl } {
     return this.form.controls;
   }

   ngOnInit(): void {
     this.getMunicipalities()
     this.getCategories()

    this.form = this.formBuilder.group({
      title: '',
      category: '',
      municipality: ''
    });
  }


  onSubmitFilter(){
    this.errorMessage = ''

    if(this.form.invalid){
      console.log(this.form)
      console.log("form is invalid")
      return;
    }

    let formData = new FormData();

    formData.append("title", this.form.get('title')!!.value)
    formData.append("category", this.form.get('category')!!.value)
    formData.append("municipality", this.form.get('municipality')!!.value)

    this.service.filterPosts(formData).subscribe((it) => {
      this.posts = it

      console.log(this.posts)
    })
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

}
