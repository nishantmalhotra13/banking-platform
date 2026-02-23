import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { ApiService, AccountSummary, CardResponse } from '../../core/services/api.service';

@Component({
  selector: 'app-account-detail',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatTabsModule, MatTableModule, MatProgressSpinnerModule, MatChipsModule],
  templateUrl: './account-detail.component.html',
  styleUrl: './account-detail.component.scss'
})
export class AccountDetailComponent implements OnInit {
  summary: AccountSummary | null = null;
  creditCards: CardResponse[] = [];
  debitCards: CardResponse[] = [];
  loading = true;
  cardCols = ['cardNumber', 'cardType', 'cardCategory', 'cardStatus', 'issuerBank'];

  constructor(private route: ActivatedRoute, private api: ApiService) {}

  ngOnInit(): void {
    const acct = this.route.snapshot.paramMap.get('accountNumber')!;
    this.api.getAccountSummary(acct).subscribe({
      next: s => {
        this.summary = s;
        this.creditCards = s.cards.filter(c => c.cardCategory === 'CREDIT');
        this.debitCards = s.cards.filter(c => c.cardCategory === 'DEBIT');
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  maskCard(num: string): string {
    if (!num || num.length < 4) return num;
    return '****' + num.slice(-4);
  }
}
