import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Usuario } from '../configuracion/model/usuario';
import { ModalService } from './detalle-usuarios/modal.service';
import Swal from 'sweetalert2';
import { UsuariosService } from '../configuracion/services/usuario.service';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit {

  pageUsuario: number =1;
 

  usuarios: Usuario[];
  paginador: any;
  usuarioSeleccionado: Usuario;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;

  constructor(private router: Router, private usuariosService: UsuariosService, private modalService: ModalService) { }

  ngOnInit(): void {
  }

  delete(usuario: Usuario): void{

    Swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el usuario ${usuario.fullName} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.usuariosService.delete(usuario.usuarioId).subscribe(
          response =>{
            this.getUsuarios();
            Swal.fire(
              'Usuario eliminado!',
              'El usuario ha sido eliminado con éxito',
              'success'
            )
          }
        )
      }
    })

  }
  
  abrirModal(usuario: Usuario){
    this.usuarioSeleccionado= usuario;
    this.modalService.abrirModal();
  }

  crearUsuario(){
    this.usuarioSeleccionado = new Usuario();
    this.modalService.abrirModal();
  }

  getUsuarios(){
    this.usuarios = null;
    this.elements = [];
    this.previous = [];
    
    this.usuariosService.getUsuariosList().subscribe(response =>{
        this.usuarios = response;        
    });

  }


}
