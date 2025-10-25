import { Component, OnChanges, OnInit } from '@angular/core';
import { CardComponent } from '../card/card.component';
import { NgIf } from '@angular/common';
import { CatService } from '../cat.service';
import { Gatto } from '../cat.service';
import { GattoFotoDTO } from '../cat.service';
import { CommonModule } from '@angular/common';
import { HostListener } from '@angular/core';

@Component({
  selector: 'app-menu',
  imports: [CardComponent,NgIf,CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{

  gatti:GattoFotoDTO[]=[];
  show:boolean=false;
  rematch=false;

  Show(){
    this.show=!this.show;
  }

  back(){
    this.show=false;
    this.rematch=false;
}
  notShow(event:string){
    this.rematch=true;
  }

  @HostListener('document:keydown.escape', ['$event'])
  handleEscKey(event: KeyboardEvent) {
    if(this.show)
      this.back(); 
  }

  chiudiModale() {
    this.show = false;
  }

  constructor(private servizioGatti:CatService) {}


  ngOnInit() {
    this.servizioGatti.httpGetGatti().subscribe(
      response => {
        this.gatti = response;
        console.log(this.gatti)
      },
      error => {
        console.error('Errore nel recupero dei gatti:', error);
      }
    );
  }
}
