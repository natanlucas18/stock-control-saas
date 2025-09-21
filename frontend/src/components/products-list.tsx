'use client';

import { columns } from '@/app/(produtos)/listar-produtos/columns';
import { DataTable } from '@/app/(produtos)/listar-produtos/data-table';
import { ProductFormType, ProductsData } from '@/types/product-schema';

import { toast } from 'react-toastify';

type ProductsListProps = {
  products: ProductsData[];
};

export const products: ProductsData[] = [
  {
    id: 1,
    name: 'Produto 1',
    price: 12.5,
    stockMin: 4,
    stockMax: 10,
    unitMeasure: 'KG',
    stockLocationId: 2
  },
  {
    id: 2,
    name: 'Produto 2',
    price: 32.44,
    stockMin: 5,
    stockMax: 15,
    unitMeasure: 'UND',
    stockLocationId: 3
  }
];

export default function ProductsList() {
  // Fazendo um POST de um produto
  const onSubmit = async (data: ProductFormType) => {
    console.log(data);
  };

  const deleteProduct = async (id: ProductsData) => {
    const response = await fetch(`http://localhost:8080/api/products/${id}`);
    if (response.status <= 205)
      return toast.success('Produto deletado com sucesso!');
    return toast.error('Erro ao deletar produto!');
  };

  return (
    <>
      <main>
        <section>
          <DataTable
            columns={columns}
            data={products}
          />
        </section>
      </main>
    </>
  );
}
