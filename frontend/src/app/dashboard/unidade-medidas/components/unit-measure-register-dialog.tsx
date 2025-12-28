import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from '@/components/ui/dialog';
import { Plus } from 'lucide-react';
import UnitMeasureRegisterForm from './unit-measure-register-form';

export default function UnitMeasureRegisterDialog() {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button className=' w-22 place-self-end lg:w-25'>
          <Plus/>
           Novo
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle className='text-center'>Cadastro de unidade de medida</DialogTitle>
          <UnitMeasureRegisterForm />
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
