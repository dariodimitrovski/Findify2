import { Component, OnInit } from '@angular/core';
import { MunicipalityService } from '../../services/municipality.service';
import { Municipality } from '../../models/Municipality';
import { CommonModule, NgFor } from '@angular/common';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostService } from '../../services/post.service';
import { Router, RouterLink } from '@angular/router';
import { HomeComponent } from '../home/home.component';
import { Category } from '../../models/Category';
import { CategoryService } from '../../services/category.service';
import { MapComponent } from '../map/map.component';
import { User } from '../../models/User';

@Component({
  selector: 'app-add-post',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HomeComponent, RouterLink, MapComponent],
  templateUrl: './add-post.component.html',
  styleUrl: './add-post.component.scss'
})
export class AddPostComponent implements OnInit {

  errorMessage: string = ''
  form!: FormGroup
  complete: boolean = true;

  constructor(private municipalityService: MunicipalityService,
    private formBuilder: FormBuilder,
    private service: PostService,
    private router: Router,
    private categoryService: CategoryService,
    private postService: PostService
  ) { }


  ngOnInit(): void {
    this.getMunicipalities()
    this.getCategories()

    this.form = this.formBuilder.group({
      title: ['', Validators.required],
      category: ['', Validators.required],
      description: ['', Validators.required],
      municipality: ['', Validators.required],
      image: ['', Validators.required],
      lostorfound: ['lost', Validators.required]
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }

  onFileSelected(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.form.get('image')?.setValue(file);
    }
  }
  

  onSubmit() {
  
    this.errorMessage = ''

    const lostOrFoundValue = this.form.get('lostorfound')!!.value;
    const state = lostOrFoundValue === "lost" ? "PENDING_LOST" : "PENDING_FOUND";

    if(this.form.invalid){
      console.log(this.form)
      console.log("form is invalid")
      this.complete = false;
      return;
    }
    
    console.log(this.form)

    let formData = new FormData();

    const currentUser: User = JSON.parse(localStorage.getItem('user')!!) as User

    formData.append("title", this.form.get('title')!!.value)
    formData.append("category", this.form.get('category')!!.value)
    formData.append("description", this.form.get('description')!!.value)
    formData.append("municipality", this.form.get('municipality')!!.value)
    formData.append("image", this.form.get('image')!!.value)
    formData.append("state", state)
    
    formData.append("lng", this.postService.getCoordinates()[0].toString())
    formData.append("lat", this.postService.getCoordinates()[1].toString())


    formData.forEach(it => console.log(it))


    this.service.addPost(formData).subscribe({
      next: () => {
        this.complete = true
        this.router.navigateByUrl('/home')
      },
      error: error => {
        this.errorMessage = error.error
        console.log("Error", error)
        this.complete = false;
      }
    })
  }

  municipalities: Municipality[] = []

  getMunicipalities() {
    this.municipalityService.getMunicipalities().subscribe((it) => {
      this.municipalities = it;
    });
  }

  categories: Category[] = []

  getCategories(){
    this.categoryService.getCategories().subscribe((it) => {
      this.categories = it;
    })
  }

}
