import { Injectable, Inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { PLATFORM_ID } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CookieService {
  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  setItem(key: number, value: string): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(key.toString(), JSON.stringify(value));
    }
  }

  getItem(key: number): string | null {
    if (isPlatformBrowser(this.platformId)) {
      const item = localStorage.getItem(key.toString());
      return item ? JSON.parse(item) : null;
    }
    return null;
  }

  removeItem(key: number): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(key.toString());
    }
  }

clear(){
    localStorage.clear();
}


}
