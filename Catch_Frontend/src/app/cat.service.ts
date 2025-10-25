import { Injectable } from '@angular/core';
import { Observable, forkJoin, map, switchMap } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';



export interface FotoGatto{
  //foto?:Observable<string>[];
  foto:Array<string>;
  numberOfPhoto:number;
}

export interface GattoFotoDTO {
  id: number;
  nome: string;
  bio: string;
  foto: string[]; // Array di immagini codificate in Base64
}

export interface Gatto{
  gallery:FotoGatto;
  id:number;
  bio:string;
  nome:string;
}

@Injectable({
  providedIn: 'root'
})

export class CatService {
  apiUrl = 'http://localhost:8080/getAllCats';
  constructor(private http: HttpClient){}

  httpGetGatti(): Observable<GattoFotoDTO[]> {
      return this.http.get<GattoFotoDTO[]>(this.apiUrl);
    }
  }

