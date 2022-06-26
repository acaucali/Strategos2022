import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DatoIdea } from '../../configuracion/model-util/DatoIdea';
import { DatoMedicion } from '../../configuracion/model-util/DatoMedicion';
import { EvaluacionIdeas } from '../../configuracion/model/evaluacionideas';
import { IdeasEvaluadas } from '../../configuracion/model/ideasevaluadas';
import { IdeasProyectos } from '../../configuracion/model/ideasproyectos';
import { EvaluacionIdeasService } from '../../configuracion/services/evaluacionidea.service';
import { EvaluacionIdeasDetalleService } from '../../configuracion/services/evaluacionideasdetalle.service';
import { IdeasProyectosService } from '../../configuracion/services/ideasproyectos.service';
import { ModalConsultaService } from '../consultaidea/modal.service';
import swal from 'sweetalert2';
import { URL_BACKEND } from 'src/app/config/config';


@Component({
  selector: 'app-evaluacion-datos',
  templateUrl: './evaluacion-datos.component.html',
  styleUrls: ['./evaluacion-datos.component.css']
})
export class EvaluacionDatosComponent implements OnInit {

  private errores: string[];
  ideaSeleccionada: IdeasProyectos = new IdeasProyectos();
  encabezados: DatoIdea[];
  ideasEvaluadas: number[];
  evaluacion: EvaluacionIdeas = new EvaluacionIdeas();
  datos: DatoMedicion[];
  valores: number[] = new Array<number>();;
  public formulario: FormGroup;
  vacio: boolean = false;  
  registrado: boolean = false;
  
  constructor(private router: Router, private activatedRoute: ActivatedRoute, private evaluacionService: EvaluacionIdeasService, private ideasService: IdeasProyectosService,
    private evaluacionDetalleService: EvaluacionIdeasDetalleService, private modalService: ModalConsultaService) { }

  ngOnInit(): void {

    this.activatedRoute.params.subscribe(params =>{
      let evaId = params['id'];

      if(evaId && evaId != 0){
        this.evaluacionService.getEvaluacion(evaId).subscribe(response =>{this.evaluacion = response}); //traer evaluacion
        //traer ideas x evaluacion
        this.evaluacionDetalleService.getEvaluacionesList(evaId).subscribe(response =>{this.ideasEvaluadas = response}); // obtiene los estatus
        this.evaluacionDetalleService.getEncabezadosList(evaId).subscribe(response =>{this.encabezados = response}); // obtiene las propuestas
        this.evaluacionDetalleService.getMedicionesList(evaId).subscribe(response =>{
          this.datos = response;     
          console.log(this.datos);     
        });
        
      }      
    });
    
  }

  calcular(){
    this.validar();
    if(this.vacio == true){
      alert("Existen Campos Vacios")
    }else{
      this.valores = null;
      this.valores = new Array<number>();
      for(let idea of this.ideasEvaluadas){      
        for(let dato of this.datos){
          if(dato.ideaId == idea){
            
            if(dato.campo =='criterio'){
              var peso= Number(dato.peso);
              var valor= Number(dato.valor);
              var calculo = (peso * valor);
              var calculoText = calculo.toFixed(2);
              this.valores.push(Number(calculoText));
            }
            if(dato.campo =='total'){
              let sum = 0;
              for (let i = 0; i < this.valores.length; i++) {
                sum += this.valores[i];
              }
              dato.valor = sum.toString();
              this.valores = null;
              this.valores = new Array<number>();
            }
          }
        }
       
      }
    }
    
  }

  registrar(){
    this.validar();
    if(this.vacio == true){
      alert("Existen Campos Vacios")
    }else{
      this.evaluacionDetalleService.registrarMediciones(this.datos, this.evaluacion.evaluacionId).subscribe(
        json =>{
          swal.fire('Mediciones registradas',  `${json.mensaje}`, 'success');
          this.registrado = true;
        },
        err =>{
          this.errores = err.error.errors as string[];
          console.error('Código error: '+err.status);
          console.error(err.error.errors);
        }
      );

    }
  }

  abrirModal(id: any){
    this.ideasService.getIdea(id).subscribe(response => this.ideaSeleccionada = response);
    console.log(this.ideaSeleccionada);
    this.modalService.abrirModal();
  }

  regresar(){
    this.router.navigate(['/', 'ponderacion']);
  }

  validar(){
    this.vacio = false;
    for(let idea of this.ideasEvaluadas){      
      for(let dato of this.datos){
        if(dato.ideaId == idea){
          
          if(dato.campo =='criterio' && dato.valor=='' || dato.valor == null){
            this.vacio = true;            
          }
        }
      }
    }
    
  }

  descargarXls(){
    window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/evaluaciondetalle/excel/" + this.evaluacion.evaluacionId); 
  }
  
}
