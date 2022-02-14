import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Controles } from '../controles';
import { ControlesService } from '../controles.service';
import { ControlesComponent } from '../controles.component';
import { Procesos } from 'src/app/procesos/procesos';
import { ProcesosService } from 'src/app/procesos/procesos.service';

@Component({
  selector: 'detalle-control',
  templateUrl: './detallecontrol.component.html',
  styleUrls: ['./detallecontrol.component.css']
})
export class DetalleControl implements OnInit {
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
