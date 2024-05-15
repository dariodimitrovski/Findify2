import { Component, OnInit } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { PostDetailsModalComponent } from '../post-details-modal/post-details-modal.component';
import { Post } from '../../models/Post';
import { PostService } from '../../services/post.service';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-pending',
  standalone: true,
  imports: [NavBarComponent, PostDetailsModalComponent, RouterLink, CommonModule],
  templateUrl: './pending.component.html',
  styleUrl: './pending.component.scss'
})
export class PendingComponent implements OnInit {

  posts: Post[] = []

  constructor(private postService: PostService,
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit(): void {
    this.postService.getPendingPosts().subscribe({
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
    })
  }
}
