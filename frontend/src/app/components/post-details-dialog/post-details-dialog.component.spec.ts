import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostDetailsDialogComponent } from './post-details-dialog.component';

describe('PostDetailsDialogComponent', () => {
  let component: PostDetailsDialogComponent;
  let fixture: ComponentFixture<PostDetailsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostDetailsDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PostDetailsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
