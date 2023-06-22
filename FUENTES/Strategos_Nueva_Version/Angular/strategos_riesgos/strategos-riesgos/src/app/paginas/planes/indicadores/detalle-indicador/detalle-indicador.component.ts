import { Component, Input, OnInit } from '@angular/core';
import { Indicador } from '../../../configuracion/model/indicador';
import { ModalService } from './modal.service';
import { IndicadorService } from '../../../configuracion/services/indicador.service';
import { Unidad } from 'src/app/paginas/configuracion/model/unidad';
import { UnidadService } from '../../../configuracion/services/unidad.service';
import swal from 'sweetalert2';
import { PerspectivaService } from '../../../configuracion/services/perspectiva.service';
import { IndicadoresComponent } from '../indicadores.component';

@Component({
  selector: 'detalle-indicador',
  templateUrl: './detalle-indicador.component.html',
  styleUrls: ['./detalle-indicador.component.css']
})
export class DetalleIndicadorComponent implements OnInit {

  private errores: string[];
  @Input() indicador: Indicador = new Indicador;
  unidades: Unidad[];
  
  objetivo: string = "";
  titulo: string = "Datos del Indicador";

  constructor(public modalservice: ModalService, public indicadorService: IndicadorService, public unidadService: UnidadService, public perspectivaService: PerspectivaService,
    private indicadorComponent: IndicadoresComponent) { }

  ngOnInit(): void {
    this.unidadService.getUnidadesList().subscribe(response =>{this.unidades = response});
    if(localStorage.getItem('objetivoId') != null){
      this.perspectivaService.getPerspectiva(localStorage.getItem('objetivoId')).subscribe(response =>{this.objetivo = response.nombre.toString()});
    }
    
  }

  create(): void{
    
    this.indicador.nombreCorto = this.indicador.nombre;
    this.indicador.organizacionId = Number(localStorage.getItem('organizacion'));
    this.indicador.naturaleza = 0;
    this.indicador.prioridad = 0;
    this.indicador.mostrarEnArbol = 1;
    this.indicadorService.create(this.indicador, localStorage.getItem('objetivoId')).subscribe(
      json => {
      swal.fire('Nuevo Indicador', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.indicadorComponent.getIndicadores();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.indicadorService.update(this.indicador).subscribe(json =>{
      swal.fire('Indicador Actualizado',  `${json.mensaje}`, 'success')
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
