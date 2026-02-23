import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { AuthService, UserInfo } from '../../core/services/auth.service';

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatChipsModule, MatProgressSpinnerModule, MatSnackBarModule],
  templateUrl: './user-management.component.html',
  styleUrl: './user-management.component.scss'
})
export class UserManagementComponent implements OnInit {
  users: UserInfo[] = [];
  loading = true;
  cols = ['username', 'fullName', 'email', 'role', 'actions'];

  constructor(private auth: AuthService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.auth.getUsers().subscribe({
      next: u => { this.users = u; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  toggleRole(user: UserInfo): void {
    const newRole = user.role === 'ADMIN' ? 'USER' : 'ADMIN';
    this.auth.changeRole(user.id, newRole).subscribe({
      next: updated => {
        const idx = this.users.findIndex(u => u.id === updated.id);
        if (idx >= 0) this.users[idx] = updated;
        this.users = [...this.users];
        this.snackBar.open(`Role changed to ${newRole}`, 'Close', { duration: 2000 });
      },
      error: () => this.snackBar.open('Failed to change role', 'Close', { duration: 3000 })
    });
  }
}
