import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { AuthService } from '../../core/services/auth.service';
import { ApiService, HealthCheck } from '../../core/services/api.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, MatCardModule, MatButtonModule, MatIconModule, MatChipsModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  health: HealthCheck | null = null;
  healthServices: [string, string][] = [];
  links: { [key: string]: string } | null = null;
  linkEntries: [string, string][] = [];

  constructor(public auth: AuthService, private api: ApiService) {}

  ngOnInit(): void {
    this.api.healthCheck().subscribe({
      next: h => { this.health = h; this.healthServices = Object.entries(h.services); },
      error: () => {}
    });
    this.api.portalLinks().subscribe({
      next: l => { this.links = l; this.linkEntries = Object.entries(l); },
      error: () => {}
    });
  }
}
