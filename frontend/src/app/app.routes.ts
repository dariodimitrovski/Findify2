import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LostItemsComponent } from './components/lost-items/lost-items.component';
import { FoundItemsComponent } from './components/found-items/found-items.component';
import { RegisterComponent } from './components/register-modal/register-modal.component';
import { LoginComponent } from './components/login-modal/login-modal.component';
import { AddPostComponent } from './components/add-post/add-post.component';
import { PendingComponent } from './components/pending/pending.component';
import { YourPostsComponent } from './components/your-posts/your-posts.component';
import { PostDetailsModalComponent } from './components/post-details-modal/post-details-modal.component';
import { UserManagementComponent } from './components/user-management/user-management.component';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';
import { MyProfileComponent } from './components/my-profile/my-profile.component';


export const routes: Routes = [
    { path: 'home', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: RegisterComponent },
    { path: 'lost-items', component: LostItemsComponent },
    { path: 'found-items', component: FoundItemsComponent },
    { path: 'add-post', component: AddPostComponent },
    { path: 'pending-posts', component: PendingComponent },
    { path: 'your-posts', component: YourPostsComponent },
    // {path: 'post-details', component:PostDetailsModalComponent},
    { path: 'posts/:id', component: PostDetailsModalComponent },
    { path: 'user-management', component: UserManagementComponent },
    { path: 'profile/:id', component: MyProfileComponent },
    { path: 'update-profile/:id', component: UpdateProfileComponent },
    // { path: 'my-profile', component: MyProfileComponent },
    { path: '**', redirectTo: 'home' }
];
