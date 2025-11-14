import { Component, OnChanges, OnDestroy, OnInit,ApplicationRef } from '@angular/core';
import { CardComponent } from '../card/card.component';
import { NgIf } from '@angular/common';
import { CatService } from '../cat.service';
import { GattoFotoDTO } from '../cat.service';
import { CommonModule } from '@angular/common';
import { HostListener } from '@angular/core';
import { catchError, Subject, EMPTY, filter, interval, Subscription, switchMap, takeUntil, take} from 'rxjs';
import { AddCatModalComponent } from "../add-cat-modal/add-cat-modal.component";
import { CookieService } from '../cookie-service.service';




@Component({
  selector: 'app-menu',
  imports: [CardComponent, NgIf, CommonModule, AddCatModalComponent],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit,OnDestroy{

  showNotification = false;
  private sub!: Subscription;
  private pollingSub!: Subscription;

  private destroy$ = new Subject<void>();
  gattiRicevuti = new Map<string, GattoFotoDTO>();
  gattiDaMostrare:GattoFotoDTO[] = [];;

  toShowResetMessage:boolean=false;
  toShowCarouselModal:boolean=false;
  toShowNewCatModal:boolean=false;
  fineProfili=false;
  profiliTerminati=false;
  profiliSkippati=false;
  private pollingSubscription: Subscription | undefined;


  closeResetMessage(){
    this.toShowResetMessage=false;
  }

  showResetMessage(){
    this.cookieService.clear();
    this.gattiRicevuti.forEach(gatto=>{gatto.voto=undefined});
    this.toShowResetMessage=true;
  }


  ngOnInit(): void {
    this.servizioGatti.httpGetGatti().subscribe(
      response => this.processaGatti(response),
      error => console.error('Errore iniziale:', error)
    );

    this.appRef.isStable.pipe(
      filter(stable => stable),
      take(1)
    ).subscribe(() => {
      interval(4000).pipe(
        takeUntil(this.destroy$),
        switchMap(() => this.servizioGatti.httpGetGatti().pipe(
          catchError(err => {
            console.error('Errore nel polling:', err);
            return EMPTY;
          })
        ))
      ).subscribe(
        response => this.processaGatti(response)
      );
    });
  }


 showNewCatModal(){
    this.toShowNewCatModal=true;
  }

  closeNewCatModal() {
    console.log("chiusura modale");
    this.toShowNewCatModal = false;
  }

  showCarouselModal(){
    this.gattiDaMostrare = Array.from(this.gattiRicevuti.values()).filter(gatto => gatto.voto === undefined);
    this.fineProfili=false;
    this.toShowCarouselModal=true;
  }

  fineDeiProfili(){
    this.fineProfili=true;
  }

  riceviVoto(event:{chiave:string,valore:string}){
    var gatto:GattoFotoDTO|undefined = this.gattiRicevuti.get(event.chiave);
    if(gatto){
      gatto!.voto=event.valore;
      this.gattiRicevuti.set(event.chiave,gatto);
      console.log("aggiunto voto "+gatto.voto+" al gatto "+event.chiave);
    }
    console.log("non trovato il gatto "+event.chiave);
  }

  closeCarouselModal(){
    this.toShowCarouselModal=false;
}


  @HostListener('document:keydown.escape', ['$event'])
  handleEscKey(event: KeyboardEvent) {
      this.closeCarouselModal(); 
      this.closeNewCatModal();
      this.closeResetMessage();
  }

  constructor(private servizioGatti:CatService, private cookieService: CookieService, private appRef: ApplicationRef) {}

private caricaGatti() {
  this.servizioGatti.httpGetGatti().subscribe(
    response => this.processaGatti(response),
    error => console.error('Errore iniziale nel recupero dei gatti:', error)
  );
}



notificaUser() {
  this.showNotification = true;
  console.log("notifica all'user");
  setTimeout(() => {
    this.showNotification = false;
  }, 3000);
}


private processaGatti(response: GattoFotoDTO[]) {
  if (typeof window !== 'undefined' && window.localStorage) {
    console.log('localStorage è disponibile');
    localStorage.setItem('test', 'ok');
  } else {
    console.warn('localStorage NON disponibile');
  }
  response.forEach(gatto => {
    if(this.gattiRicevuti.has(gatto.id!.toString())){
      console.log("gatto "+gatto.id!+" già caricato in precedenza");
    }
    else
    {
      const cookieValue: string | null = this.cookieService.getItem(gatto.id!);
      if(cookieValue!==null){
        gatto.voto = cookieValue!;
      }
      
      this.gattiRicevuti.set(gatto.id!.toString(), gatto);
      console.log("nuovo gatto: " + gatto.id);
      this.profiliSkippati=false;
      this.profiliTerminati=false;
      this.notificaUser();
      console.log("nuovo gatto");
    }
  });
}

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }


    
}
