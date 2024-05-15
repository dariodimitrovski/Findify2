import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule,  ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { UserDTO } from '../../models/UserDTO';
import { HomeComponent } from '../home/home.component';
import { NgIf } from '@angular/common';
import {CommonModule} from '@angular/common';
import { PhoneDirective } from '../../directives/phone.directive';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, RouterLink, HomeComponent, NgIf, CommonModule, PhoneDirective],
  templateUrl: './register-modal.component.html',
  styleUrl: './register-modal.component.scss'
})
export class RegisterComponent implements OnInit {

  errorMessage = ''
  submitted = false
  form!: FormGroup

  constructor(private formBuilder: FormBuilder,
    private auth: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['',  [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(40)
      ]],
      phoneNumber: ['',  [
        Validators.required,
        Validators.minLength(11),
        Validators.maxLength(11)
      ]],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }


  onSubmit(): void{
    console.log('ova e formata' ,this.form);
    this.errorMessage = ''
    this.submitted = true
    
    if(this.form.invalid){
      console.log('invalid form', this.form);
      debugger;
      return;
    }

    const data = this.form.value
    console.log('ova e data', data)

    const user: UserDTO = {
      firstName: data.firstName,
      lastName: data.lastName,
      password: data.password,
      phoneNumber: data.phoneNumber,
      email: data.email
    }

    console.log('ova e user', user)

    this.auth.registerUser(user).subscribe({
      next: () => {
        this.router.navigateByUrl('/home/login')
      },
      error: error => {
        this.errorMessage = error.error
        console.log("Error", error)
      }
    })
  }


}
