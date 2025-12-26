import { Button } from "@/components/ui/button";
import { FormControl, FormField, FormItem, FormLabel, Form, FormMessage } from "@/components/ui/form";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useUrlParams } from "@/hooks/use-url-params";
import { reportProductsFormFiltersSchema, ReportProductsFormFiltersType } from "@/types/report-products-form-filters-schema";
import { zodResolver } from "@hookform/resolvers/zod";
import { Eraser, FileText, SlidersHorizontal} from "lucide-react";
import { useForm } from "react-hook-form";

export function ReportProductsFormFilters () {
    const hookForm = useForm<ReportProductsFormFiltersType>({
        resolver: zodResolver(reportProductsFormFiltersSchema),
    })
    const {setUrlParam, params} = useUrlParams()

    const onSubmit = (formFilters: ReportProductsFormFiltersType) => {
        setUrlParam('status', formFilters.status);
        setUrlParam('page', '1');
    }

    const clearParams = () => {
        setUrlParam('status', '')
    }

    return (
        <div>
            <Form {...hookForm}>
                <form onSubmit={hookForm.handleSubmit(onSubmit)}
                    className="flex flex-col md:flex-row md:justify-between lg:flex-row lg:justify-between">
                    <FormField
                        control={hookForm.control}
                        name="status"
                        render={({field}) => (
                        <FormItem>
                            <FormLabel>Status</FormLabel>
                            <FormControl>
                                <Select
                                    onValueChange={field.onChange}
                                    value={field.value}
                                >
                                   <SelectTrigger className='w-26 md:w-30 lg:w-36'>
                                        <SelectValue placeholder='Status' />
                                    </SelectTrigger>
                                    <SelectContent>
                                        <SelectItem value='HIGH'>Alto</SelectItem>
                                        <SelectItem value='NORMAL'>Normal</SelectItem>
                                        <SelectItem value='LOW'>Baixo</SelectItem>
                                    </SelectContent> 
                                </Select>
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}/>
                    <div className='flex gap-2'>
                    <Button 
                      type='reset' 
                      className='mt-2 w-8 h-8 md:mt-5.5 md:w-12 md:h-12 lg:mt-5.5 lg:w-14 cursor-pointer'
                      onClick={() => clearParams()}
                    >
                        <Eraser/>
                    </Button>
                    <Button
                      type='submit'
                      className='mt-2 w-8 h-8 md:mt-5.5 md:w-12 md:h-12 lg:mt-5.5 lg:w-14 cursor-pointer'
                    >
                        <SlidersHorizontal/>
                    </Button>
                    <Button
                      type='button'
                      className='mt-2 w-8 h-8 md:mt-5.5 md:w-12 md:h-12 lg:mt-5.5 lg:w-14 cursor-pointer'
                    >
                        <FileText/>
                    </Button>  
                    </div>
                </form>
            </Form>
        </div>
    );
}