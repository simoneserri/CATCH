import { Component, OnInit, Output, SimpleChanges } from '@angular/core';
import { CarouselComponent } from '../carousel/carousel.component';
import { CommonModule } from '@angular/common';
import { Input } from '@angular/core';
import { CatService, GattoFotoDTO} from '../cat.service';
import { Subscription } from 'rxjs';
import { EventEmitter } from '@angular/core';
import { CookieService } from '../cookie-service.service';

@Component({
  selector: 'app-card',
  imports: [CarouselComponent,CommonModule],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})

export class CardComponent implements OnInit{
  @Output() risultatoVoto = new EventEmitter<{ chiave: string, valore: string }>();
  @Output() fineProfili = new EventEmitter<void>();
  @Input() gatti!:GattoFotoDTO[];
  profiliSkippati:boolean=false;
  selected:number=0;
  gattoAttuale:GattoFotoDTO | undefined = undefined;
  private subscription!: Subscription;

 constructor(private catService: CatService,private cookieService: CookieService) {}

 ngOnInit() {
  if(this.gatti.length==0){
      this.fineProfili.emit();
  }
  else{
    this.gattoAttuale=this.gatti[0];
  }
}

rivediProfili(){
  console.log("reset");
  this.selected=0;
  this.gattoAttuale = this.gatti[this.selected];
}

  votazione(event: string) {
    if(event==="skip")
    {
      console.log("non settiamo cookie, voto skippato");
      this.nextCat();
    }
    else{
      this.risultatoVoto.emit({chiave:this.gattoAttuale!.id!.toString(),valore:event});
      this.cookieService.setItem(this.gattoAttuale!.id!,event);
      console.log("Settiamo cookie, voto al gatto "+this.gattoAttuale!.id!+": "+event);
      console.log("Cancelliamo gatto dall'array");
      this.deleteAndNext();
    }

  }

deleteAndNext() {
  this.gatti.splice(this.selected, 1);
  if (this.selected < this.gatti.length) {
    this.gattoAttuale = this.gatti[this.selected];
  } else {
    console.log('Gatti terminati');
    this.fineProfili.emit();
    this.gattoAttuale = undefined;
  }
}

nextCat(){
  if (this.selected < this.gatti.length - 1) {
    this.selected++;
    this.gattoAttuale = this.gatti[this.selected];
  } else {
    console.log('Gatti terminati');
    this.fineProfili.emit();
    this.gattoAttuale = undefined;
  }
}


 
}



