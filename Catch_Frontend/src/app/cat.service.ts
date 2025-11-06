import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable} from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Subject } from 'rxjs';


export interface GattoFotoDTO {
  id?: number;
  nome: string;
  bio: string;
  foto: string[]; 
  voto?: string;
  skippato?: boolean;
}


@Injectable({
  providedIn: 'root'
})

export class CatService {

  apiUrlFetch = 'http://localhost:8080/getAllCats';

  apiUrlSend = 'http://localhost:8080/addCat';

addCat(catData: any): Observable<HttpResponse<any>> {
  return this.http.post<any>('http://localhost:8080/addCat', catData, {
    headers: { 'Content-Type': 'application/json' }, observe: 'response', responseType: 'text' as 'json'
  });
}

constructor(private http: HttpClient) {}

  private resetCarousel = new Subject<void>();
  triggerResetCarousel$ = this.resetCarousel.asObservable();
  private gattiSubject = new BehaviorSubject<GattoFotoDTO[]>([]);
  gatti$ = this.gattiSubject.asObservable();

  triggerReset() {
    this.resetCarousel.next();
  }


  httpGetGatti(): Observable<GattoFotoDTO[]> {
      return this.http.get<GattoFotoDTO[]>(this.apiUrlFetch);
    }
  }

