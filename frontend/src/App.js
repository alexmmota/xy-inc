import React, { Component } from 'react';
import './App.css';
import { Button, Row, Col, Input} from 'react-materialize';
import $ from 'jquery';
import Notifications, {notify} from 'react-notify-toast';

class App extends Component {

constructor() {
    super();
    this.state = {
      id: '',
      name:'', 
      description: '', 
      category:'CLOTHES', 
      price: '0.00',
      listProducts: [],
      errors: []
    };
  }

  componentWillMount() {
    this.getAllProducts();

    this.setName = this.setName.bind(this);
    this.setDescription = this.setDescription.bind(this);
    this.setCategory = this.setCategory.bind(this);
    this.setPrice = this.setPrice.bind(this);
  }

  getAllProducts() {
    this.setState({errors: []});
    $.ajax({
      url: 'http://localhost:8080/zup/products',
      contentType:'application/json',
      dataType:'json',
      type:'get',
      success: function(resposta){
        if(resposta) {
          this.setState({listProducts: resposta});
        } else {
          this.setState({listProducts: []});
        }
      }.bind(this),
      error: function(resposta){
        console.log("erro");
      }
    });
  }

  clearProduct(){
    this.setState({
      id: '',
      name: '',
      description: '',
      category: 'CLOTHES',
      price: '0.00',
      errors: []
    });
  }

  setName(event) {
    this.setState({name:event.target.value});
  }

  setDescription(event) {
    this.setState({description:event.target.value});
  }

  setCategory(event) {
    this.setState({category:event.target.value});
  }

  setPrice(event) {
    var aux = event.target.value.replace('.','');
    var auxN = parseInt(aux,10);
    aux = auxN.toString();
    if(aux.length === 0)
      aux = '000';
    if(aux.length === 1)
      aux = '00'.concat(aux);
    else if(aux.length === 2)
      aux = '0'.concat(aux);
    aux = aux.substr(0, aux.length-2).concat('.').concat(aux.substr(aux.length-2));

    this.setState({price:aux});
  }

  sendForm(event) {
    event.preventDefault();
    var product = {
      id: this.state.id,
      name: this.state.name,
      description: this.state.description,
      category: this.state.category,
      price: this.state.price
    };

    if(product.id) {
      this.updateProduct(product);
    } else {
      this.saveNewProduct(product);
    }
  }

  updateProduct(product, id) {
    $.ajax({
      url: 'http://localhost:8080/zup/products/'+product.id,
      contentType:'application/json',
      dataType:'json',
      type:'put',
      data: JSON.stringify(product),
      success: function(resposta){
        this.setState({errors: []});
        this.clearProduct();
        this.getAllProducts();
        notify.show('Produto atualizado com sucesso','success',3000);
      }.bind(this),
      error: function(resposta){
        if(resposta.status === 400) {
          this.setState({errors: resposta.responseJSON});
        }
        notify.show('Erro ao atualizar Produto','error',3000);
        console.log("erro"+resposta);
      }.bind(this)
    });
  }

  saveNewProduct(product) {
    $.ajax({
      url: 'http://localhost:8080/zup/products/',
      contentType:'application/json',
      dataType:'json',
      type:'post',
      data: JSON.stringify(product),
      success: function(resposta){
        this.setState({errors: []});
        this.clearProduct();
        this.getAllProducts();
        notify.show('Produto criado com sucesso','success',4000);
      }.bind(this),
      error: function(resposta){
        if(resposta.status === 400) {
          this.setState({errors: resposta.responseJSON});
        }
        notify.show('Erro ao criar Produto','error',4000);
        console.log("erro"+resposta);
      }.bind(this)
    });
  }

  callForm(id) {
    this.setState({errors: []});
    $.ajax({
      url: 'http://localhost:8080/zup/products/'+id,
      contentType:'application/json',
      dataType:'json',
      type:'get',
      success: function(resposta){
        this.setState({
          id: resposta.id,
          name: resposta.name,
          description: resposta.description,
          category: resposta.category,
          price: resposta.price,
        });
      }.bind(this),
      error: function(resposta){
        console.log("erro");
      }
    });    
  }

  removeItem(id, event) {
    if(id) {
      $.ajax({
        url: 'http://localhost:8080/zup/products/'+id,
        contentType:'application/json',
        dataType:'json',
        type:'delete',
        success: function(resposta){
          this.getAllProducts();
          this.clearProduct();
          notify.show('Produto removido com sucesso','success',4000);
        }.bind(this),
        error: function(resposta){
          notify.show('Falha ao remover produto','error',4000);
          console.log("erro");
        }
      });
    } else {
      notify.show('Escolha um produto para remover','warning',4000);
    }
  }

  render() {
    return (
      <div className="App">
        <div className="App-header">
          <h2>Zup Dashboard</h2>
        </div>

        <Notifications />

        <ul>
          {
            this.state.errors.map(function (item, i) {
              return <li key={i}>{item}</li>
            })
          }
        </ul>

        <Row>
          <Col s={6}>
            <div style={{textAlign: 'left'}}>
              <button type="button" onClick={this.clearProduct.bind(this)} className="btn-add btn">Novo</button>
            </div>
            <table className="striped">
              <thead>
                <tr>
                    <th data-field="id">Id</th>
                    <th data-field="name">Nome</th>
                    <th data-field="category">Categoria</th>
                    <th data-field="price">Preço</th>
                </tr>
              </thead>

              <tbody>
                {
                  this.state.listProducts.map(function (item, i) {
                    return <tr key={i} onClick={()=>this.callForm(item.id)} style={{cursor: 'pointer'}}><td>{item.id}</td><td>{item.name}</td><td>{item.category}</td><td>R$ {item.price}</td></tr>
                  }.bind(this))
                }
              </tbody>
            </table>          

          </Col>

          <Col s={6}>
            <h4>Produto</h4>

            <form role="form" onSubmit={this.sendForm.bind(this)} method="post">
              <Row>
                  <Input s={12} label="Id" placeholder="" value={this.state.id} disabled />
                  <Input s={12} label="Nome" placeholder="Nome" onChange={this.setName} value={this.state.name} />
                  <Input s={12} label="Descrição" placeholder="Descrição" onChange={this.setDescription} value={this.state.description} />
                  <Input s={12} type='select' label="Categoria" defaultValue='2' onChange={this.setCategory} value={this.state.category}>
                    <option value='CLOTHES'>Roupa</option>
                    <option value='ELECTRONIC'>Eletrônicos</option>
                    <option value='GAMES'>Jogos</option>
                    <option value='BOOKS'>Livros</option>
                  </Input>
                  <Input s={12} label="Preço" placeholder="Preço" onChange={this.setPrice} value={this.state.price} />
                  <Row>
                    <Col s={6}><Button type="submit" waves='light'>Salvar</Button></Col>
                    <Col s={6}><button type="button" onClick={this.removeItem.bind(this, this.state.id)} className="btn-delete btn">Excluir</button></Col>
                  </Row>
              </Row>
            </form>
          
          </Col>
        </Row>

      </div>
    );
  }
}

export default App;
