import { Tooltip, TooltipContent, TooltipTrigger } from './ui/tooltip';

type customTooltipProps = {
  children: React.ReactNode;
  content: React.ReactNode;
};

export default function CustomTooltip({
  children,
  content
}: customTooltipProps) {
  return (
    <Tooltip>
      <TooltipTrigger asChild>{children}</TooltipTrigger>
      <TooltipContent>
        <p>{content}</p>
      </TooltipContent>
    </Tooltip>
  );
}
