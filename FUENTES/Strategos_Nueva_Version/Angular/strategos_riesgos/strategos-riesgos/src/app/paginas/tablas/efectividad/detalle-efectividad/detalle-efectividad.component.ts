import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EfectividadControles } from 'src/app/paginas/configuracion/model/efectividad';
import { EfectividadService } from 'src/app/paginas/configuracion/services/efectividad.service';
import swal from 'sweetalert2';
import { EfectividadComponent } from '../efectividad.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'detalle-efectividad',
  templateUrl: './detalle-efectividad.component.html',
  styleUrls: ['./detalle-efectividad.component.css']
})
export class DetalleEfectividadComponent implements OnInit {

  private errores: string[];

  @Input() efectividad: EfectividadControles;

  titulo: string = "Datos efectividad";
  constructor(private efectividadService: EfectividadService, private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private efectividadComponent: EfectividadComponent
   ) { }

  ngOnInit() {
  }

  
 
  create(): void{
    this.efectividadService.create(this.efectividad).subscribe(
      json => {
      swal.fire('Nueva Efectividad', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.efectividadComponent.getEfectividades();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.efectividadService.update(this.efectividad).subscribe(json =>{
      swal.fire('Efectividad Actualizada',  `${json.mensaje}`, 'success')
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
