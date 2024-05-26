import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { LoginComponent } from '../login-modal/login-modal.component';
import { RegisterComponent } from '../register-modal/register-modal.component';
import { AddPostComponent } from '../add-post/add-post.component';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/User';
import { UserService } from '../../services/user.service';
import { PendingComponent } from '../pending/pending.component';
import { YourPostsComponent } from '../your-posts/your-posts.component';
import { UpdateProfileComponent } from '../update-profile/update-profile.component';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [RouterLink, LoginComponent, RegisterComponent, AddPostComponent, PendingComponent, YourPostsComponent, UpdateProfileComponent],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent implements OnInit {

  loggedIn: boolean = false;
  currentUser: User | undefined
  userImage: any
  dropdownOpened: boolean = false;

  constructor(private authService: AuthService, private router: Router, private userService: UserService, private sanitizer: DomSanitizer) {
     this.currentUser = JSON.parse(localStorage.getItem('user')!!) //TODO: Check do we need this
   }

   ngOnInit() {
    this.loggedIn = this.authService.isLoggedIn();

    this.authService.getLoginStatus().subscribe((isLoggedIn) => {
      this.loggedIn = isLoggedIn;
    });

    this.authService.getCurrentUser().subscribe((user) => {
      this.currentUser = user;
    })

  }

  toggleDropdown() {
    this.dropdownOpened = !this.dropdownOpened;
  }

  logOut() {
    console.log('logging out')
    this.authService.updateLoginStatus(false);

    this.router.navigate(['/home']);

    this.authService.logout()
  }
}
