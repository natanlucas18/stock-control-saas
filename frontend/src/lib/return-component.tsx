import { Badge } from '@/components/ui/badge';

enum statusStatus {
  HIGH = 'HIGH',
  NORMAL = 'NORMAL',
  LOW = 'LOW'
}

export function returnBadgeComponent(status: string | undefined) {
  if (status === statusStatus.HIGH)
    return (
      <Badge
        variant={'secondary'}
        className='w-16 bg-blue-500 text-white dark:bg-blue-950'
      >
        Alto
      </Badge>
    );
  if (status === statusStatus.NORMAL)
    return (
      <Badge
        variant={'default'}
        className='w-16'
      >
        Normal
      </Badge>
    );
  if (status === statusStatus.LOW)
    return (
      <Badge
        variant={'destructive'}
        className='w-16'
      >
        Baixo
      </Badge>
    );
}
