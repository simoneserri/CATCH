import { Component, OnInit, Output } from '@angular/core';
import { CarouselComponent } from '../carousel/carousel.component';
import { CommonModule } from '@angular/common';
import { Input } from '@angular/core';
import { Gatto } from '../cat.service';
import { GattoFotoDTO } from '../cat.service';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-card',
  imports: [CarouselComponent,CommonModule],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})

export class CardComponent implements OnInit{

  @Output('profiliTerminati') profiliTerminati = new EventEmitter<string>();
  @Input() gatti!:GattoFotoDTO[];
  selected:number=0;

  votazione(event: string) {
    this.nextCat();
  }

  nextCat(){
    this.selected+=1;
    if(this.gatti.length==this.selected){
      this.profiliTerminati.emit('');
    }
  }
  constructor() {}
  ngOnInit(): void {
  }
}



