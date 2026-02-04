import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle
} from '@/components/ui/card';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import EntryMovementsForm from './components/entry-movements-form';

export default async function Movements() {
  return (
    <Tabs
      defaultValue='entrada'
      className='w-[400px]'
    >
      <TabsList>
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
      <TabsContent value='saida'>Change your password here.</TabsContent>
      <TabsContent value='devolucao'>Change your password here.</TabsContent>
      <TabsContent value='transferencia'>
        Change your password here.
      </TabsContent>
    </Tabs>
  );
}
