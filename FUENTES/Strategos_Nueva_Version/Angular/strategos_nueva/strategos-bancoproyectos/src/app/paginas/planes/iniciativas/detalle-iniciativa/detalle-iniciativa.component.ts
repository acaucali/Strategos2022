import { Component, Input, OnInit } from '@angular/core';
import { Unidad } from 'src/app/paginas/configuracion/model/unidad';
import { IniciativaService } from 'src/app/paginas/configuracion/services/iniciativa.service';
import { PerspectivaService } from 'src/app/paginas/configuracion/services/perspectiva.service';
import { UnidadService } from 'src/app/paginas/configuracion/services/unidad.service';
import { Iniciativa } from '../../../configuracion/model/iniciativa';
import { ModalService } from './modal.service';
import swal from 'sweetalert2';
import { IniciativasComponent } from '../iniciativas.component';

@Component({
  selector: 'detalle-iniciativa',
  templateUrl: './detalle-iniciativa.component.html',
  styleUrls: ['./detalle-iniciativa.component.css']
})
export class DetalleIniciativaComponent implements OnInit {

  private errores: string[];
  @Input() iniciativa: Iniciativa = new Iniciativa;
  unidades: Unidad[];
  
  objetivo: string = "";
  titulo: string = "Datos de la Iniciativa";

  constructor(public modalservice: ModalService, public iniciativaService: IniciativaService, public unidadService: UnidadService, public perspectivaService: PerspectivaService,
    private iniciativaComponent: IniciativasComponent) { }

  ngOnInit(): void {
    this.unidadService.getUnidadesList().subscribe(response =>{this.unidades = response});
    if(localStorage.getItem('objetivoId') != null){
      this.perspectivaService.getPerspectiva(localStorage.getItem('objetivoId')).subscribe(response =>{this.objetivo = response.nombre.toString()});
    }
  }

  create(): void{
    
    this.iniciativaService.create(this.iniciativa, localStorage.getItem('objetivoId')).subscribe(
      json => {
      swal.fire('Nueva Iniciativa', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.iniciativaComponent.getIniciativas();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.iniciativaService.update(this.iniciativa).subscribe(json =>{
      swal.fire('Iniciativa Actualizada',  `${json.mensaje}`, 'success')
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
