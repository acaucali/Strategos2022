import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TipoImpacto } from '../tipoimpacto';
import { TipoimpactoService } from '../tipoimpacto.service';
import { TiposImpactoComponent } from '../tipos-impacto.component';




@Component({
  selector: 'detalle-impacto',
  templateUrl: './detalleimpacto.component.html',
  styleUrls: ['./detalleimpacto.component.css']
})
export class DetalleImpacto implements OnInit {
  private errores: string[];

  @Input() impacto: TipoImpacto;

  titulo: string = "Datos del Tipo Impacto";
  constructor(private impactoService: TipoimpactoService, private router: Router, 
    private activatedRoute: ActivatedRoute, private impactoComponent: TiposImpactoComponent, 
    public modalservice: ModalService
   ) { }

  ngOnInit() {
  }

  
 
  create(): void{
    this.impactoService.create(this.impacto).subscribe(
      json => {
      swal.fire('Nuevo Tipo Impacto', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.impactoComponent.getImpactos();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.impactoService.update(this.impacto).subscribe(json =>{
      swal.fire('Tipo Impacto Actualizado',  `${json.mensaje}`, 'success')
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
