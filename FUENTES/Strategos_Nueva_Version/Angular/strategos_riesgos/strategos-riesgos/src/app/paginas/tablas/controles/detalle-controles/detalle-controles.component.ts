import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ModalService } from './modal.service';
import swal from 'sweetalert2';
import { Controles } from 'src/app/paginas/configuracion/model/controles';
import { Procesos } from 'src/app/paginas/configuracion/model/procesos';
import { ControlesService } from 'src/app/paginas/configuracion/services/controles.service';
import { ProcesosService } from 'src/app/paginas/configuracion/services/procesos.service';
import { ControlesComponent } from '../controles.component';

@Component({
  selector: 'detalle-control',
  templateUrl: './detalle-controles.component.html',
  styleUrls: ['./detalle-controles.component.css']
})
export class DetalleControlesComponent implements OnInit {

  private errores: string[];
  
  public procesos: Procesos[];
  @Input() control: Controles;

  titulo: string = "Datos del control";
  constructor(private controlService: ControlesService, private router: Router, 
    private activatedRoute: ActivatedRoute, private controlComponent: ControlesComponent,
    public modalservice: ModalService, private procesosService: ProcesosService
   ) { }

  ngOnInit() {
    this.procesosService.getProcesos().subscribe(response => this.procesos= response);
  }

  
 
  create(): void{
    this.controlService.create(this.control).subscribe(
      json => {
      swal.fire('Nuevo Control', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.controlComponent.getControles();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.controlService.update(this.control).subscribe(json =>{
      swal.fire('Control Actualizado',  `${json.mensaje}`, 'success')
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
