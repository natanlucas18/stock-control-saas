'use client';

import { Card, CardContent } from '@/components/ui/card';
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger
} from '@/components/ui/sheet';
import { MovementsReport } from '@/types/movements-report-schema';

type MovementsReportDetailsSheetProps = {
  report?: MovementsReport;
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
  trigger?: React.ReactNode;
};

export default function MovementsReportDetailsSheet({
  report,
  open,
  onOpenChange,
  trigger
}: MovementsReportDetailsSheetProps) {
  return (
    <Sheet
      open={open}
      onOpenChange={onOpenChange}
    >
      {trigger && (
        <SheetTrigger
          className='cursor-pointer'
          asChild
        >
          {trigger}
        </SheetTrigger>
      )}
      <SheetContent className='px-4 overflow-scroll'>
        <SheetHeader>
          <SheetTitle>Detalhes do Relatório</SheetTitle>
        </SheetHeader>

        <Card>
          <CardContent className='space-y-2'></CardContent>
        </Card>
      </SheetContent>
    </Sheet>
  );
}
