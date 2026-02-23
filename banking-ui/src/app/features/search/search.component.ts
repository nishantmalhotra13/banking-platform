import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ApiService, FinalResponse } from '../../core/services/api.service';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [CommonModule, FormsModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule,
            MatTabsModule, MatTableModule, MatProgressSpinnerModule, MatSnackBarModule],
  templateUrl: './search.component.html',
  styleUrl: './search.component.scss'
})
export class SearchComponent {
  phoneValue = '';
  nameValue = '';
  emailValue = '';
  results: FinalResponse[] = [];
  loading = false;
  searched = false;
  displayedColumns = ['accountNumber', 'productCode', 'cards'];

  constructor(private api: ApiService, private router: Router, private snackBar: MatSnackBar) {}

  onTabChange(e: any): void {
    this.results = [];
    this.searched = false;
  }

  searchPhone(): void {
    this.loading = true;
    this.searched = true;
    this.api.searchByPhone(this.phoneValue).subscribe({
      next: r => { this.results = r; this.loading = false; },
      error: e => this.handleError(e)
    });
  }

  searchName(): void {
    this.loading = true;
    this.searched = true;
    this.api.searchByName(this.nameValue).subscribe({
      next: r => { this.results = r; this.loading = false; },
      error: e => this.handleError(e)
    });
  }

  searchEmail(): void {
    this.loading = true;
    this.searched = true;
    this.api.searchByEmail(this.emailValue).subscribe({
      next: r => { this.results = r; this.loading = false; },
      error: e => this.handleError(e)
    });
  }

  goToAccount(accountNumber: string): void {
    this.router.navigate(['/accounts', accountNumber]);
  }

  private handleError(e: any): void {
    this.loading = false;
    this.snackBar.open(e.error?.message || 'Search failed', 'Close', { duration: 3000 });
  }
}
