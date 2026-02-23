import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface AuthResponse {
  accessToken: string;
  tokenType: string;
  expiresIn: number;
}

export interface UserInfo {
  id: number;
  username: string;
  email: string;
  fullName: string;
  role: string;
  enabled: boolean;
}

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly baseUrl = environment.apiBaseUrl;
  private loggedIn$ = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) {}

  get isLoggedIn$(): Observable<boolean> { return this.loggedIn$.asObservable(); }
  get isLoggedIn(): boolean { return this.hasToken(); }

  get isAdmin(): boolean {
    const payload = this.decodeToken();
    return payload?.roles === 'ADMIN';
  }

  get currentUser(): string {
    const payload = this.decodeToken();
    return payload?.name || payload?.sub || '';
  }

  get token(): string | null {
    return localStorage.getItem('access_token');
  }

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/auth/login`, { username, password })
      .pipe(tap(res => this.storeToken(res.accessToken)));
  }

  register(username: string, password: string, email: string, fullName: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/auth/register`, { username, password, email, fullName })
      .pipe(tap(res => this.storeToken(res.accessToken)));
  }

  logout(): void {
    localStorage.removeItem('access_token');
    this.loggedIn$.next(false);
  }

  getMe(): Observable<UserInfo> {
    return this.http.get<UserInfo>(`${this.baseUrl}/auth/me`);
  }

  getUsers(): Observable<UserInfo[]> {
    return this.http.get<UserInfo[]>(`${this.baseUrl}/auth/users`);
  }

  changeRole(id: number, role: string): Observable<UserInfo> {
    return this.http.put<UserInfo>(`${this.baseUrl}/auth/users/${id}/role`, { role });
  }

  private storeToken(token: string): void {
    localStorage.setItem('access_token', token);
    this.loggedIn$.next(true);
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('access_token');
  }

  private decodeToken(): any {
    const token = this.token;
    if (!token) return null;
    try {
      const payload = token.split('.')[1];
      return JSON.parse(atob(payload));
    } catch {
      return null;
    }
  }
}

