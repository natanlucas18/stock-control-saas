import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle
} from '@/components/ui/card';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import EntryMovementsForm from './components/entry-movements-form';
import ExitMovementsForm from './components/exit-movements-form';
import ReturnMovementsForm from './components/return-movements-form';
import TransferMovementsForm from './components/transfer-movements-form';

export default async function Movements() {
  return (
    <Tabs
      defaultValue='entrada'
      className='mx-auto w-full max-w-2xl'
    >
      <TabsList className='mx-auto'>
        <TabsTrigger value='entrada'>Entrada</TabsTrigger>
        <TabsTrigger value='saida'>Saída</TabsTrigger>
        <TabsTrigger value='devolucao'>Devolução</TabsTrigger>
        <TabsTrigger value='transferencia'>Transferência</TabsTrigger>
      </TabsList>
      <TabsContent value='entrada'>
        <Card>
          <CardHeader>
            <CardTitle>ENTRADA</CardTitle>
            <CardDescription>
              Registrar entrada de produtos no estoque.
            </CardDescription>
          </CardHeader>
          <CardContent>
            <EntryMovementsForm />
          </CardContent>
        </Card>
      </TabsContent>
      <TabsContent value='saida'>
        <Card>
          <CardHeader>
            <CardTitle>SAÍDA</CardTitle>
            <CardDescription>
              Registrar saída de produtos no estoque.
            </CardDescription>
          </CardHeader>
          <CardContent>
            <ExitMovementsForm />
          </CardContent>
        </Card>
      </TabsContent>
      <TabsContent value='devolucao'>
        <Card>
          <CardHeader>
            <CardTitle>DEVOLUÇÃO</CardTitle>
            <CardDescription>
              Registrar devolução de produtos no estoque.
            </CardDescription>
          </CardHeader>
          <CardContent>
            <ReturnMovementsForm />
          </CardContent>
        </Card>
      </TabsContent>
      <TabsContent value='transferencia'>
        <Card>
          <CardHeader>
            <CardTitle>TRANSFERÊNCIA</CardTitle>
            <CardDescription>
              Registrar transferência de produtos entre estoques.
            </CardDescription>
          </CardHeader>
          <CardContent>
            <TransferMovementsForm />
          </CardContent>
        </Card>
      </TabsContent>
    </Tabs>
  );
}
