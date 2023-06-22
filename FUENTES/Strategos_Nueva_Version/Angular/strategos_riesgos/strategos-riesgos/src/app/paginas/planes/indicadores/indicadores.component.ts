import { Component, OnInit } from '@angular/core';
import { IndicadorService } from '../../configuracion/services/indicador.service';
import { ModalService } from './detalle-indicador/modal.service';
import { Indicador } from '../../configuracion/model/indicador';
import Swal from 'sweetalert2';
import { ModalIndicadorService } from './medicion-indicador/modal.service';
import { Router } from '@angular/router';
import { ModalGraficoService } from './grafico/config-grafico/modal.service';


@Component({
  selector: 'app-indicadores',
  templateUrl: './indicadores.component.html',
  styleUrls: ['./indicadores.component.css']
})
export class IndicadoresComponent implements OnInit {

  pageIndi: number =1;
  public indicadores: Indicador[];

  indicadorSeleccionado: Indicador;

  constructor(public modalservice: ModalService, public indicadorService: IndicadorService, public modalIndicador: ModalIndicadorService, private router: Router,
    public modalGrafico: ModalGraficoService) { }

  ngOnInit(): void {
    localStorage.removeItem('indicadorNombre');
    localStorage.removeItem('indicadorNombre');
    this.getIndicadores();
  }

  crearIndicador(){
    if(localStorage.getItem('objetivoId') != null){
      this.indicadorSeleccionado = new Indicador();
      this.modalservice.abrirModal(); 
    }
          
    
  }

  editarIndicador(ind: Indicador){
    
    this.indicadorSeleccionado = ind;
    this.modalservice.abrirModal(); 
  }

  eliminarIndicador(ind: Indicador){
   
    Swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el indicador?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.indicadorService.delete(ind.indicadorId).subscribe(
          response =>{
            this.getIndicadores();
            Swal.fire(
              'Indicador eliminado!',
              'El indicador ha sido eliminado con éxito',
              'success'
            )
          }
        )
      }
    })

  }

  getIndicadores(){
    this.indicadores = [];
    
    if(localStorage.getItem('objetivoId') != null){
      this.indicadorService.getIndicadoresPerspectiva(Number(localStorage.getItem('objetivoId'))).subscribe(response =>{
        this.indicadores = response;
      });
    }
    
  }

  cargarMedicion(){
    if(localStorage.getItem('objetivoId') != null){
      this.modalIndicador.abrirModal();
    }
  }

  graficar(ind: Indicador){    

    localStorage.setItem('indicador', ind.indicadorId.toString());
    localStorage.setItem('indicadorNombre', ind.nombre.toString());
    
    this.modalGrafico.abrirModal();

  }

}
