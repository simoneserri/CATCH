import { Component } from '@angular/core';
import { MenuComponent} from './menu/menu.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  imports:[MenuComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Catch';
}
