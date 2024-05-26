import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/User';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { Router, RouterLink } from '@angular/router';
import { UserDTO } from '../../models/UserDTO';
import { PhoneDirective } from '../../directives/phone.directive';

@Component({
  selector: 'app-update-profile',
  standalone: true,
  imports: [ReactiveFormsModule, PhoneDirective, RouterLink],
  templateUrl: './update-profile.component.html',
  styleUrl: './update-profile.component.scss'
})
export class UpdateProfileComponent implements OnInit {

  currentUser: User | undefined
  form!: FormGroup
  errorMessage: string = ''

  constructor(private authService: AuthService,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private auth: AuthService,
    private userSerivce: UserService) {
    this.currentUser = JSON.parse(localStorage.getItem('user')!!)
  }

  ngOnInit() {
    this.authService.getCurrentUser().subscribe((user) => {
      this.currentUser = user;
    })

    this.form = this.formBuilder.group({
      firstName: null,
      lastName: null,
      password: null,
      phoneNumber: null,
      image: null
    });
  }

  onFileSelected(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.form.get('image')?.setValue(file);
    }
  }

  onSubmit(): void{
    if(this.form.invalid){
      console.log("nevalidna")
      console.log(this.form)
      return;
    }

    let formData = new FormData();

    if(this.form.controls['firstName'].value != null){
    formData.append("firstName", this.form.get('firstName')!!.value)
    }
    if(this.form.controls['lastName'].value != null){
    formData.append("lastName", this.form.get('lastName')!!.value)
    }
    if(this.form.controls['phoneNumber'].value != null){
    formData.append("phoneNumber", this.form.get('phoneNumber')!!.value)
    }
    if(this.form.controls['password'].value != null){
    formData.append("password", this.form.get('password')!!.value)
    }
    formData.append("image", this.form.get('image')!!.value)

    this.userService.updateProfile(formData)
    .subscribe({
      next: (updatedUser: User) => {
        this.currentUser = updatedUser;
  
        localStorage.setItem('user', JSON.stringify(updatedUser));

        this.authService.setCurrentUser(updatedUser);
  
        this.router.navigateByUrl('/app-my-profile');
      },
      error: error => {
        this.errorMessage = error.error;
        console.log("Error", error);
      }
    });
  
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }


}
