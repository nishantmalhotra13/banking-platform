import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface FinalResponse {
  accountNumber: string;
  productCode: string;
  cards: CardResponse[];
}

export interface CardResponse {
  cardNumber: string;
  cardType: string;
  cardCategory: string;
  cardStatus: string;
  issuerBank: string;
}

export interface AccountSummary {
  accountNumber: string;
  productCode: string;
  name: string;
  phone: string;
  email: string;
  status: string;
  branchCode: string;
  cards: CardResponse[];
}

export interface CardValidation {
  valid: boolean;
  card: CardResponse | null;
}

export interface HealthCheck {
  overallStatus: string;
  services: { [key: string]: string };
}

@Injectable({ providedIn: 'root' })
export class ApiService {

  private readonly base = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  searchByPhone(phoneNumber: string): Observable<FinalResponse[]> {
    return this.http.post<FinalResponse[]>(`${this.base}/api/v1/search/phone`, { phoneNumber });
  }

  searchByName(fullName: string): Observable<FinalResponse[]> {
    return this.http.post<FinalResponse[]>(`${this.base}/api/v1/search/name`, { fullName });
  }

  searchByEmail(email: string): Observable<FinalResponse[]> {
    return this.http.post<FinalResponse[]>(`${this.base}/api/v1/search/email`, { email });
  }

  getAccountCards(accountNumber: string): Observable<CardResponse[]> {
    return this.http.get<CardResponse[]>(`${this.base}/api/v1/accounts/${accountNumber}/cards`);
  }

  getCreditCards(accountNumber: string): Observable<CardResponse[]> {
    return this.http.get<CardResponse[]>(`${this.base}/api/v1/accounts/${accountNumber}/cards/credit`);
  }

  getDebitCards(accountNumber: string): Observable<CardResponse[]> {
    return this.http.get<CardResponse[]>(`${this.base}/api/v1/accounts/${accountNumber}/cards/debit`);
  }

  getAccountSummary(accountNumber: string): Observable<AccountSummary> {
    return this.http.get<AccountSummary>(`${this.base}/api/v1/accounts/${accountNumber}/summary`);
  }

  validateCard(cardNumber: string): Observable<CardValidation> {
    return this.http.post<CardValidation>(`${this.base}/api/v1/cards/validate`, { cardNumber });
  }

  healthCheck(): Observable<HealthCheck> {
    return this.http.get<HealthCheck>(`${this.base}/api/v1/health/downstream`);
  }

  portalLinks(): Observable<{ [key: string]: string }> {
    return this.http.get<{ [key: string]: string }>(`${this.base}/api/v1/portal/links`);
  }
}

