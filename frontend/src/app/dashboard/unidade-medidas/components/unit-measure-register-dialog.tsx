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
import { useState } from 'react';

export default function UnitMeasureRegisterDialog() {
  const [open, setOpen] = useState(false)
  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button className=' w-22 place-self-end lg:w-25'>
          <Plus/>
           Novo
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle className='text-center'>Cadastro de unidade de medida</DialogTitle>
          <UnitMeasureRegisterForm onClosedDialog={() => setOpen(false)}/>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
