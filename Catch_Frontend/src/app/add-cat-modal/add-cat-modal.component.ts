import { Component, EventEmitter, numberAttribute, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CatService, GattoFotoDTO } from '../cat.service';

@Component({
  selector: 'app-add-cat-modal',
  imports:[CommonModule, FormsModule],
  templateUrl: './add-cat-modal.component.html',
  styleUrl: './add-cat-modal.component.scss'
})
export class AddCatModalComponent {

  thankYouPage: string|null =null;
  blobCaricati: Blob[] = [];
  fotoCaricate: string[] = [];
  nomeGatto: string = "";
  bioGatto: string = "";
  @Output() chiudiModale = new EventEmitter<void>(); 
  
  rimuoviFoto(index:number){
    console.log("rimossa immagine");
    this.fotoCaricate.splice(index, 1);
    this.blobCaricati.splice(index,1);
  }

  ngOnDestroy() {
  this.thankYouPage=null;
}

constructor(private catService: CatService) {}

blobToBase64(blob: Blob): Promise<string> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onloadend = () => {
      const result = reader.result as string;
      const base64 = result.replace(/^data:image\/[a-z]+;base64,/, '');
      resolve(base64);
    };
    reader.onerror = reject;
    reader.readAsDataURL(blob);
  });
}


submitCat() {
  const base64Promises = this.blobCaricati.map(blob => {
    return new Promise<string>((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        const result = reader.result as string;
        const base64 = result.replace(/^data:image\/[a-z]+;base64,/, '');
        resolve(base64);
      };
      reader.onerror = reject;
      reader.readAsDataURL(blob);
    });
  });

Promise.all(base64Promises)
  .then((fotoPulite: string[]) => {
    const cat: GattoFotoDTO = {
      nome: this.nomeGatto,
      bio: this.bioGatto,
      foto: fotoPulite
    };

    console.log("Invio:", cat);

    this.catService.addCat(cat).subscribe({
      next: response => {
        if (response.status === 200) {
          this.thankYouPage = "Gatto inserito correttamente!";
        } else {
          this.thankYouPage = "Ops, qualcosa è andato storto. Riprova!";
        }
      },
      error: err => {
        const status = err?.status ?? 'sconosciuto';
        console.error("Errore HTTP:", err);
      }
    });
  })
  .catch(error => {
    console.error("Errore nella conversione dei Blob:", error);
    this.thankYouPage = "Errore nella preparazione delle immagini";
  });
}

  closeCatModal(){
    this.chiudiModale.emit();
  }


  onFileSelected(event: Event){
    console.log("inserita immagine");
    const input = event.target as HTMLInputElement;
  if (input.files && input.files.length > 0) {
    const file = input.files[0];
    this.blobCaricati.push(file);
    this.fotoCaricate.push(URL.createObjectURL(file));
  }

}



}
