import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Efectos } from '../efectos';
import { EfectosService } from '../efectos.service';
import { EfectosComponent } from '../efectos.component';


@Component({
  selector: 'detalle-efecto',
  templateUrl: './detalleefecto.component.html',
  styleUrls: ['./detalleefecto.component.css']
})
export class DetalleEfecto implements OnInit {
  private errores: string[];

  @Input() efecto: Efectos;

  titulo: string = "Datos del efecto";
  constructor(private efectoService: EfectosService, private router: Router, 
    private activatedRoute: ActivatedRoute, private efectoComponent: EfectosComponent,
    public modalservice: ModalService
   ) { }

  ngOnInit() {
  }

  
 
  create(): void{
    this.efectoService.create(this.efecto).subscribe(
      json => {
      swal.fire('Nuevo Efecto', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.efectoComponent.getEfectos();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.efectoService.update(this.efecto).subscribe(json =>{
      swal.fire('Efecto Actualizado',  `${json.mensaje}`, 'success')
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
