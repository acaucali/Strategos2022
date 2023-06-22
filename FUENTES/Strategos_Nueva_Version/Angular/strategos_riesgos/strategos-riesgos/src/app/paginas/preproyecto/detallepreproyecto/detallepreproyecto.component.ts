import { AfterViewInit, Component, Input, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';

import { IniciativaEstatusStrategos } from '../../configuracion/model-util/IniciativaEstatusStrategos';
import { MetodologiaStrategos } from '../../configuracion/model-util/MetodologiaStrategos';
import { OrganizacionStrategos } from '../../configuracion/model-util/organizacionstrategos';
import { TipoProyectoStrategos } from '../../configuracion/model-util/TipoProyectoStrategos';
import { IdeasProyectos } from '../../configuracion/model/ideasproyectos';
import { Proyectos } from '../../configuracion/model/proyectos';
import { TipoPoblacion } from '../../configuracion/model/tipopoblacion';
import { TiposObjetivos } from '../../configuracion/model/tiposobjetivos';
import { IniciativaEstatusStrategosService } from '../../configuracion/services-util/iniciativaestatusstrategos.service';
import { MetodologiaStrategosService } from '../../configuracion/services-util/metodologiastrategos.service';
import { OrganizacionStrategosService } from '../../configuracion/services-util/organizacionstrategos.service';
import { TipoProyectoStrategosService } from '../../configuracion/services-util/tipoproyectostrategos.service';
import { IdeasProyectosService } from '../../configuracion/services/ideasproyectos.service';
import { TipoPoblacionService } from '../../configuracion/services/tipopoblacion.service';
import { TiposObejtivosService } from '../../configuracion/services/tiposobjetivos.service';
import { GestionideasComponent } from '../../gestionideas/gestionideas.component';
import { ModalService } from './modal.service';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { map } from 'rxjs/operators';
import swal from 'sweetalert2';
import { ProyectoService } from '../../configuracion/services/proyectos.service';
import { PreproyectoComponent } from '../preproyecto.component';

interface Item {
  text: String;
  value: number;
}

@Component({
  selector: 'detalle-preproyecto',
  templateUrl: './detallepreproyecto.component.html',
  styleUrls: ['./detallepreproyecto.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class DetallepreproyectoComponent implements OnInit, AfterViewInit {

  titulo: string = "Datos del Preproyecto - Preproyectos";
  private errores: string[];

  organizaciones: OrganizacionStrategos[];
  metodologias: MetodologiaStrategos[];
  alineaciones: TiposObjetivos[];
  estatus: IniciativaEstatusStrategos[];
  tipos: TipoProyectoStrategos[];
  ideas: Array<IdeasProyectos>;
  poblaciones: TipoPoblacion[];
  poblacionesProyecto: TipoPoblacion[] = new Array<TipoPoblacion>();
  idea: IdeasProyectos = new IdeasProyectos();   

  @Input() preproyecto: Proyectos = new Proyectos();  

  
  dropdownSettings:IDropdownSettings={};
  dropdownSettingsOrganizacion:IDropdownSettings={};
  dropdownSettingsIdea:IDropdownSettings={};

  myForm:FormGroup;
  disabled = false;
  ShowFilter = false;
  limitSelection = false;

  selectedItems: Array<any> = [];
  disableBangalore: any;

  public source: Array<Item> = [];
  public sourceOrg: Array<{ text: String; value: number }> = [];

  public data: Array<Item>;
  public dataOrg: Array<{ text: String; value: number }>; 
  public dataOrg2: Array<{ text: String; value: number }>; 

  public selectedValue = 2;

  list: any;

  

  constructor(public modalService: ModalService, private router: Router, private organizacionService: OrganizacionStrategosService, private ideaService: IdeasProyectosService,
    private tipoService: TipoProyectoStrategosService, private alineacionService: TiposObejtivosService, private estatusService: IniciativaEstatusStrategosService,
    private metodologiaService: MetodologiaStrategosService, private poblacionService: TipoPoblacionService, private fb: FormBuilder, private proyectoService: ProyectoService,
    private preproyectoComponent: PreproyectoComponent) { }

  ngOnInit(): void {

    this.estatusService.getIniciativaEstatusList().subscribe(response =>{this.estatus = response}); // obtiene los estatus
    this.organizacionService.getOrganizacionesList().subscribe(response =>{this.organizaciones = response});
    this.alineacionService.getTiposObjetivoList().subscribe(response =>{this.alineaciones = response});
    this.metodologiaService.getMetodologiasList().subscribe(response =>{this.metodologias = response});
    this.tipoService.getTipoProyectosList().subscribe(response =>{this.tipos = response});
    this.ideaService.getIdeasList().subscribe(response =>{this.ideas = response});
    this.poblacionService.getPoblacionesList().subscribe(response =>{this.poblaciones = response});

    this.ideaService.getIdeasList().subscribe(response =>{
      this.ideas = response;
      if(this.ideas.length >0){
        this.ideas.forEach(idea =>{
          this.source.push({text: idea.nombreIdea, value: idea.ideaId});
        });
      }
      this.data = this.source;
    });

    this.organizacionService.getOrganizacionesList().subscribe(response =>{
      this.organizaciones = response;
      if(this.organizaciones.length >0){
        this.organizaciones.forEach(org =>{
          this.sourceOrg.push({text: org.nombre, value: org.organizacionId});
        });
      }
      this.dataOrg = this.sourceOrg;
      this.dataOrg2 = this.sourceOrg;
    });

        
    this.dropdownSettings = {
        singleSelection: false,
        idField: 'tipoPoblacionId',
        textField: 'poblacion',
        selectAllText: 'Seleccionar Todo',
        unSelectAllText: 'Quitar Todo',
        limitSelection: -1,
        clearSearchFilter: true,
        maxHeight: 197,
        itemsShowLimit: 3,
        allowSearchFilter: true,
        closeDropDownOnSelection: false,
        showSelectedItemsAtTop: false,
    };

    

    this.myForm = this.fb.group({
        city: [this.selectedItems]
    });

    

  }

  ngAfterViewInit(): void{
    
  }

  
  cerrarModal(){
    this.modalService.cerrarModal();
  }

  onItemSelect(item: any) {
    var tipo: TipoPoblacion = new TipoPoblacion();
    tipo.tipoPoblacionId = item.tipoPoblacionId;
    tipo.poblacion = item.poblacion;
    this.poblacionesProyecto.push(tipo);
  }
  onSelectAll(items: any) {
    for(let item of items){
      var tipo: TipoPoblacion = new TipoPoblacion();
      tipo.tipoPoblacionId = item.tipoPoblacionId;
      tipo.poblacion = item.poblacion;
      this.poblacionesProyecto.push(tipo);
    }
  }

  unSelect(item: any) {
    this.poblacionesProyecto = this.poblacionesProyecto.filter(pobl => pobl.tipoPoblacionId != item.tipoPoblacionId);
  }
  unSelectAll(items: any) {    
    this.poblacionesProyecto = new Array<TipoPoblacion>();
  }

  public seleccionarIdea(idea: any): void {
    console.log("idea", idea.value);
    this.ideaService.getIdea(idea.value).subscribe(ideaR => this.idea = ideaR);
    this.preproyecto.ideaId = this.idea.ideaId;
    this.preproyecto.anioFormulacion = this.idea.anioFormulacion;
    this.preproyecto.contactoEmail = this.idea.contactoEmail;
    this.preproyecto.contactoTelefono = this.idea.contactoTelefono; 
    this.preproyecto.tipoObjetivoId = this.idea.tipoObjetivoId;
    this.preproyecto.financiacion = this.idea.financiacion;
    this.preproyecto.objetivoGeneral = this.idea.objetivoGeneral;
    this.preproyecto.problematica = this.idea.problematica;
    this.preproyecto.profesionalResponsable = this.idea.personaEncargada;  
  }

  public seleccionarOrg(org: any): void {
    this.preproyecto.dependenciaId = org.value; 
  }

  public seleccionarOrgLid(org: any): void {
    this.preproyecto.dependenciaLider = org.value; 
  }
 
  handleLimitSelection() {
      if (this.limitSelection) {
          this.dropdownSettings = Object.assign({}, this.dropdownSettings, { limitSelection: 2 });
      } else {
          this.dropdownSettings = Object.assign({}, this.dropdownSettings, { limitSelection: null });
      }
  }

  handleFilter(value) {
    this.data = this.source.filter(
      (s) => s.text.toLowerCase().indexOf(value.toLowerCase()) !== -1
    );
  }

  handleFilterOrg(value) {
    this.dataOrg = this.sourceOrg.filter(
      (s) => s.text.toLowerCase().indexOf(value.toLowerCase()) !== -1
    );
  }

  handleFilterOrg2(value) {
    this.dataOrg2 = this.sourceOrg.filter(
      (s) => s.text.toLowerCase().indexOf(value.toLowerCase()) !== -1
    );
  }

  create(){
    
    this.preproyecto.poblaciones = this.poblacionesProyecto;
    console.log(this.preproyecto);

    this.proyectoService.create(this.preproyecto).subscribe(
      json => {
        swal.fire('Nuevo Preproyecto', `${json.mensaje}`, 'success');
        this.cerrarModal();
        this.preproyectoComponent.getPreproyectos();
        //this.ideaComponent.getIdeas();
      },
      err =>{
        this.errores = err.error.errors as string[];
        console.error('Código error: '+err.status);
        console.error(err.error.errors);
      }
    );
    
  }

  update(){

    this.proyectoService.update(this.preproyecto, 1).subscribe(json =>{
      swal.fire('Preproyecto Actualizado',  `${json.mensaje}`, 'success')
      this.cerrarModal();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );

  }

  

}


  