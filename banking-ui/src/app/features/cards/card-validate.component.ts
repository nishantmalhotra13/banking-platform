import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ApiService, CardValidation } from '../../core/services/api.service';

@Component({
  selector: 'app-card-validate',
  standalone: true,
  imports: [CommonModule, FormsModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule],
  templateUrl: './card-validate.component.html',
  styleUrl: './card-validate.component.scss'
})
export class CardValidateComponent {
  cardNumber = '';
  result: CardValidation | null = null;
  loading = false;

  constructor(private api: ApiService) {}

  validate(): void {
    this.loading = true;
    this.result = null;
    this.api.validateCard(this.cardNumber).subscribe({
      next: r => { this.result = r; this.loading = false; },
      error: () => { this.result = { valid: false, card: null }; this.loading = false; }
    });
  }
}
