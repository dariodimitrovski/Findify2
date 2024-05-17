import { Component, OnInit } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { PostDetailsModalComponent } from '../post-details-modal/post-details-modal.component';
import { Post } from '../../models/Post';
import { PostService } from '../../services/post.service';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DomSanitizer } from '@angular/platform-browser';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-pending',
  standalone: true,
  imports: [NavBarComponent, PostDetailsModalComponent, RouterLink, CommonModule, MatPaginatorModule],
  templateUrl: './pending.component.html',
  styleUrl: './pending.component.scss'
})
export class PendingComponent implements OnInit {

  posts: Post[] = []

  totalItems = this.getItemSize; //tuka od backend da se zema broj na total items
  pageSize = 10;
  currentPage = 0;

  constructor(private postService: PostService,
    private sanitizer: DomSanitizer
  ) { }

  pageChanged(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.getPendingItems(this.currentPage, this.pageSize);
  }

  ngOnInit(): void {
    this.getItemSize();

    this.getPendingItems(this.currentPage, this.pageSize);
  }

  getPendingItems(page: number, size: number): void{
    this.postService.getPendingPosts(page, size).subscribe({
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

  getItemSize() {
    this.postService.getPendingItemsSize().subscribe({
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
}
