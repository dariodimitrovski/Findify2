import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { PostService } from '../../services/post.service';
import { Post } from '../../models/Post';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-your-posts',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './your-posts.component.html',
  styleUrl: './your-posts.component.scss'
})
export class YourPostsComponent implements OnInit {

  posts: Post[] = []
  currentUser: User = JSON.parse(localStorage.getItem('user')!!)

  constructor(private postService: PostService,
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit(): void {
    this.postService.getPostsByUserId(this.currentUser.id).subscribe({
      next: (data) => {
        this.posts = data.filter(p => p.state.includes("ACTIVE"));
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
    })
  }


}
