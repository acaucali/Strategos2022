import { Component, Input, OnInit } from '@angular/core';
import { Usuario } from '../../configuracion/model/usuario';

@Component({
  selector: 'detalle-usuarios',
  templateUrl: './detalle-usuarios.component.html',
  styleUrls: ['./detalle-usuarios.component.css']
})
export class DetalleUsuariosComponent implements OnInit {

  private errores: string[];
  @Input() usuario: Usuario = new Usuario;

  titulo: string = "Datos del usuario";
  constructor() { }

  ngOnInit(): void {
  }

}
