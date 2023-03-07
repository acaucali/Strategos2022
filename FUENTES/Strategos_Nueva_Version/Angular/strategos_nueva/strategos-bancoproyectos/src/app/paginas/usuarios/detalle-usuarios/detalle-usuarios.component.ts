import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Usuario } from '../../configuracion/model/usuario';
import { UsuariosService } from '../../configuracion/services/usuario.service';
import { UsuariosComponent } from '../usuarios.component';
import { ModalService } from './modal.service';
import swal from 'sweetalert2';

@Component({
  selector: 'detalle-usuarios',
  templateUrl: './detalle-usuarios.component.html',
  styleUrls: ['./detalle-usuarios.component.css']
})
export class DetalleUsuariosComponent implements OnInit {

  private errores: string[];
  @Input() usuario: Usuario = new Usuario;

  titulo: string = "Datos del usuario";
  constructor(public modalservice: ModalService, private router: Router, private usuariosService: UsuariosService, private usuarioComponent: UsuariosComponent) { }

  ngOnInit(): void {
  }

  create(): void{
    
    this.usuariosService.create(this.usuario).subscribe(
      json => {
      swal.fire('Nuevo Usuario', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.usuarioComponent.getUsuarios();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.usuariosService.update(this.usuario).subscribe(json =>{
      swal.fire('Usuario Actualizado',  `${json.mensaje}`, 'success')
      this.cerrarModal();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  cerrarModal(){
    this.modalservice.cerrarModal();
  }


}
