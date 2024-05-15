import { ComponentFixture, TestBed } from '@angular/core/testing';

import { YourPostsComponent } from './your-posts.component';

describe('YourPostsComponent', () => {
  let component: YourPostsComponent;
  let fixture: ComponentFixture<YourPostsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [YourPostsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(YourPostsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
