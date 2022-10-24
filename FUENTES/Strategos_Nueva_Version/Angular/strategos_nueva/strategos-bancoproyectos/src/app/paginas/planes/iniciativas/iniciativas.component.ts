import { Component, OnInit } from '@angular/core';
import { Iniciativa } from '../../configuracion/model/iniciativa';
import { IniciativaService } from '../../configuracion/services/iniciativa.service';
import { ModalService } from './detalle-iniciativa/modal.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-iniciativas',
  templateUrl: './iniciativas.component.html',
  styleUrls: ['./iniciativas.component.css']
})
export class IniciativasComponent implements OnInit {

  pageInic: number =1;
  iniciativas: Iniciativa[];

  iniciativaSeleccionada: Iniciativa;



  constructor(public modalservice: ModalService, private iniciativaService: IniciativaService) { }

  ngOnInit(): void {
  }

  crearIniciativa(){
    if(localStorage.getItem('objetivoId') != null){
      this.iniciativaSeleccionada = new Iniciativa();
      this.modalservice.abrirModal(); 
    }
          
    
  }

  editarIniciativa(ini: Iniciativa){
    
    this.iniciativaSeleccionada = ini;
    this.modalservice.abrirModal(); 
  }

  eliminarIniciativa(ini: Iniciativa){
   
    Swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar la iniciativa?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.iniciativaService.delete(ini.iniciativaId).subscribe(
          response =>{
            this.getIniciativas();
            Swal.fire(
              'Iniciativa eliminada!',
              'La iniciativa ha sido eliminada con éxito',
              'success'
            )
          }
        )
      }
    })

  }

  getIniciativas(){
    this.iniciativas = [];
    
    if(localStorage.getItem('objetivoId') != null){
      this.iniciativaService.getIniciativasPerspectiva(Number(localStorage.getItem('objetivoId'))).subscribe(response =>{
        this.iniciativas = response;
      });
    }
    
  }

}
