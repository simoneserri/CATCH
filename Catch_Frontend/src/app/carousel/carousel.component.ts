import { Component, OnChanges, OnInit } from '@angular/core';
import { CarouselModule } from 'primeng/carousel';
import {PasswordModule} from 'primeng/password';
import {ButtonModule} from 'primeng/button';
import { CommonModule } from '@angular/common';
import { TagModule } from 'primeng/tag';
import { GattoFotoDTO} from '../cat.service';
import { Input } from '@angular/core';
import { NgFor } from '@angular/common';
import { SimpleChanges } from '@angular/core';
import { Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { HostListener } from '@angular/core';


@Component({
  selector: 'app-carousel',
  imports: [CarouselModule,PasswordModule,ButtonModule,CommonModule,TagModule,NgFor],
  templateUrl: './carousel.component.html',
  styleUrl: './carousel.component.scss'
})

export class CarouselComponent implements OnChanges{

@Input() gatto!: GattoFotoDTO;
@Output() voto = new EventEmitter<string>();
color:string="white";

onImageError() {
  console.warn('Immagine non caricata correttamente');
}

@HostListener('document:keydown.ArrowRight', ['$event'])
handleRightKey(event: KeyboardEvent) {
  this.nextImage();
}

@HostListener('document:keydown.ArrowLeft', ['$event'])
handleLeftKey(event: KeyboardEvent) {
  this.prevImage();
}

vota(voto:string){
  
  console.log("emettiamo voto"+voto);
  this.voto.emit(voto);
}

like(){
  this.color="green";
}

dislike(){

}

skip(){
}

ngOnChanges(changes: SimpleChanges){
  this.changeCurrentIndex(0);
}


changeCurrentIndex(index:number){
  this.currentIndex=index;
}


  currentIndex = 0;

  nextImage() {
    if (this.currentIndex < this.gatto.foto.length-1) {
      this.currentIndex++;
    } 
  }

  prevImage() {
    if (this.currentIndex > 0) {
      this.currentIndex--;
    } 
  }

  get transformStyle() {
    return `translateX(-${this.currentIndex * 100}%)`;
  }
}