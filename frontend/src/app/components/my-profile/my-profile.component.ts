import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { AuthService } from '../../services/auth.service';
import { RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-my-profile',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './my-profile.component.html',
  styleUrl: './my-profile.component.scss'
})
export class MyProfileComponent implements OnInit {
  currentUser: User | undefined
  userImage: any;

  constructor(private authService: AuthService, private userService: UserService, private sanitizer: DomSanitizer) {
    this.currentUser = JSON.parse(localStorage.getItem('user')!!)
  }

 ngOnInit() {
     this.userService.getUserImage(this.currentUser?.id!!).subscribe((imageDate) => {
      const imageUrl = URL.createObjectURL(new Blob([imageDate]));
      this.userImage = this.sanitizer.bypassSecurityTrustUrl(imageUrl);
   })
   
 }
}
