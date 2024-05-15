import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { UserService } from '../../services/user.service';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [NgFor],
  templateUrl: './user-management.component.html',
  styleUrl: './user-management.component.scss'
})
export class UserManagementComponent implements OnInit {

  users: User[] = []

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe((it) => {
      this.users = it
    })
  }

  deleteAccount(userId: number) {
    this.users = this.users.filter(user => user.id !== userId);

    this.userService.deleteUser(userId).subscribe()
  }

}
