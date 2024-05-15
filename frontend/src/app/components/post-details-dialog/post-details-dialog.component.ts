import { Component } from '@angular/core';
import { PostDetailsModalComponent } from '../post-details-modal/post-details-modal.component';

@Component({
  selector: 'app-post-details-dialog',
  standalone: true,
  imports: [PostDetailsModalComponent],
  templateUrl: './post-details-dialog.component.html',
  styleUrl: './post-details-dialog.component.scss'
})
export class PostDetailsDialogComponent {

}
